package com.minebarteksa.orion.multiblock;

import com.minebarteksa.orion.Orion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class OrionMultiBlocks
{
    private static int nextId = 0;
    static final RegistryNamespaced<ResourceLocation, MultiBlockInfo> REGISTRY = new RegistryNamespaced<>();

    private static void register(MultiBlockInfo mBlock)
    {
        REGISTRY.register(nextId, mBlock.getName(), mBlock);
        nextId++;
    }

    public static int getMultiBlockId(ResourceLocation multiblock) { return REGISTRY.getIDForObject(REGISTRY.getObject(multiblock)); }

    public static void autoRegister()
    {
        List<ModContainer> active = Loader.instance().getActiveModList();

        for(ModContainer mod : active)
        {

            File mbFolder;
            try { mbFolder = new File(mod.getClass().getClassLoader().getResource("assets/" + mod.getModId() + "/multiblocks/").toURI()); }
            catch(Exception e) { continue; }
            try
            {
                for(File f : mbFolder.listFiles())
                {
                    String j[] = new String[] {""};
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));
                    reader.lines().forEachOrdered(l -> { if(l != null) j[0] += l.replace(" ", ""); });
                    String json = j[0];
                    MultiBlockInfo mbi = MultiBlockInfo.createFromJsonFile(json, new ResourceLocation(mod.getModId(), f.getName().replace(".json", "")));
                    if(mbi != null)
                        register(mbi);
                    else
                        Orion.log.error("Error while trying to read form json file for '" + new ResourceLocation(mod.getModId(), f.getName().replace(".json", "")) + "' MultiBlock");
                }
            }
            catch(Exception e)
            {
                Orion.log.error("Error while trying to read MultiBlocks form mod '" + mod.getModId() + "'");
                Orion.log.error(e.toString());
            }
        }
    }
}
