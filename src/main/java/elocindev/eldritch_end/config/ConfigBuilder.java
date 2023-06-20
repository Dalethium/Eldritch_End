package elocindev.eldritch_end.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import elocindev.eldritch_end.config.entries.ClientConfig;
import elocindev.eldritch_end.config.entries.PrimordialAbyssConfig;
import net.fabricmc.loader.api.FabricLoader;

public class ConfigBuilder {
    public static final Gson BUILDER = (new GsonBuilder()).setPrettyPrinting().create();
  
    // Folders
    public static final Path Folder = FabricLoader.getInstance().getConfigDir().resolve("eldritch_end");
    public static final Path BiomeFolder = Folder.resolve("biomes");

    // Files
    public static final Path ClientConfig = Folder.resolve("eldritch_end-client.json");
    public static final Path PrimordialAbyss = BiomeFolder.resolve("primordial_abyss.json");
    public static final Path HasturianWastes = BiomeFolder.resolve("hasturian_wastes.json");
    
    public static boolean hasStarted() {
        return createFolders();
    }

    // CLIENTSIDE CONFIGS
    public static ClientConfig loadClientConfig() {
        try {            
            if (Files.notExists(ClientConfig)) {
                ClientConfig defaultCfg = new ClientConfig();
                
                defaultCfg.enable_fog = true;

                String defaultJson = BUILDER.toJson(defaultCfg);
                Files.writeString(ClientConfig, defaultJson);
            }

            return BUILDER.fromJson(Files.readString(ClientConfig), ClientConfig.class);

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // BIOME RELATED CONFIGS

    public static PrimordialAbyssConfig loadPrimordialAbyss() {
        try {            
            if (Files.notExists(PrimordialAbyss)) {
                PrimordialAbyssConfig defaultCfg = new PrimordialAbyssConfig();
                
                defaultCfg.enabled = true;
                defaultCfg.spawn_endermen = false;
                defaultCfg.spawn_aberrations = true;
                defaultCfg.biome_weight = 1.5F;
                defaultCfg.biome_temperature = 0.0085F;

                String defaultJson = BUILDER.toJson(defaultCfg);
                Files.writeString(PrimordialAbyss, defaultJson);
            }

            return BUILDER.fromJson(Files.readString(PrimordialAbyss), PrimordialAbyssConfig.class);

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // MISC

    public static boolean createFolders() {
        try {
            if (Files.notExists(Folder)) Files.createDirectory(Folder);
            if (Files.notExists(BiomeFolder)) Files.createDirectory(BiomeFolder);

            return true;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
