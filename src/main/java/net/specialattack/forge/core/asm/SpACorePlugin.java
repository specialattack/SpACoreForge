package net.specialattack.forge.core.asm;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.specialattack.forge.core.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@IFMLLoadingPlugin.TransformerExclusions({ "net.specialattack.forge.core.asm" })
@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.SortingIndex(100)
public class SpACorePlugin implements IFMLLoadingPlugin, IFMLCallHook {

    protected static boolean debug = false;
    public static final Logger LOG = LogManager.getLogger("SpACore-ASM");

    public static boolean stateManager, stateManagerDebug;
    public static boolean loggerTransformer;
    public static boolean debugScreen;

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "net.specialattack.forge.core.asm.SpACoreModTransformer", // Note: this one is preferably always first
                "net.specialattack.forge.core.asm.SpACoreSyncTransformer", //
                "net.specialattack.forge.core.asm.SpACoreDebugGuiTransformer", //
                "net.specialattack.forge.core.asm.SpACoreGLTransformer", // Note: this one is preferably always last
        };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return "net.specialattack.forge.core.asm.SpACorePlugin";
    }

    @Override
    public void injectData(Map<String, Object> data) {
        if (data != null) {
            if (data.containsKey("mcLocation")) {
                try {
                    ConfigManager.configFolder = new File(((File) data.get("mcLocation")).getCanonicalFile(), "config");
                } catch (IOException e) {
                    throw new IllegalStateException("Failed getting Minecraft config directory", e);
                }
            }
            if (data.containsKey("runtimeDeobfuscationEnabled") && data.get("runtimeDeobfuscationEnabled") == Boolean.FALSE) {
                ConfigManager.debug = true;
            }
        }
    }

    @Override
    public String getAccessTransformerClass() {
        return "net.specialattack.forge.core.asm.SpACoreAccessTransformer";
    }

    @Override
    public Void call() {
        Configuration config = new Configuration(new File("config" + File.separator + "spacore-asm.cfg"));
        config.setCategoryComment("client", "Client specific ASM injections");
        Property stateManager = config.get("client", "GLStateManagerExp", false, "EXPERIMENTAL! Set to true to enable handling of the render state to increase performance.");
        SpACorePlugin.stateManager = stateManager.getBoolean();
        Property stateManagerDebug = config.get("client", "GLStateManagerOutput", false, "Set to true to enable outputting of transformed classes after having the state manager injected");
        SpACorePlugin.stateManagerDebug = stateManagerDebug.getBoolean();
        Property loggerTransformer = config.get("client", "TextureLoggerTransformer", true, "Set to true to enable surpressing long stacktraces in the log when there are missing textures.");
        SpACorePlugin.loggerTransformer = loggerTransformer.getBoolean();
        Property debugScreen = config.get("client", "DebugScreen", true, "Set to true to change the debug screen (F3) to look more like the 1.8 debug screen.");
        SpACorePlugin.debugScreen = debugScreen.getBoolean();
        if (config.hasChanged()) {
            config.save();
        }
        return null;
    }

}
