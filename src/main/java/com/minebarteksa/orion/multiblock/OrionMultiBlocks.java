package com.minebarteksa.orion.multiblock;

import com.minebarteksa.orion.Orion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public class OrionMultiBlocks
{
    private static int nextId = 0;
    static final RegistryNamespaced<ResourceLocation, MultiBlockInfo> REGISTRY = new RegistryNamespaced<>();

    private static void register(MultiBlockInfo mBlock)
    {
        Orion.log.info("Registering '" + mBlock.getName() + "' MultiBlock!");
        REGISTRY.register(nextId, mBlock.getName(), mBlock);
        nextId++;
    }

    public static int getMultiBlockId(ResourceLocation multiblock) { return REGISTRY.getIDForObject(REGISTRY.getObject(multiblock)); }

    public static void autoRegister()
    {
        List<ModContainer> active = Loader.instance().getActiveModList();

        for(ModContainer mod : active)
        {
            List<Path> paths = new ArrayList<>();
            boolean isJar = false;
            try
            {
                URI u = mod.getClass().getClassLoader().getResource("assets/" + mod.getModId() + "/multiblocks/").toURI();
                Path p;
                if(u.getScheme().equals("jar"))
                {
                    FileSystem fs = FileSystems.newFileSystem(u, Collections.<String, Object>emptyMap());
                    p = fs.getPath("assets/" + mod.getModId() + "/multiblocks/");
                    isJar = true;
                }
                else
                    p = Paths.get(u);
                Stream<Path> w = Files.walk(p, 1);
                boolean isFirst = true;
                for (Iterator<Path> it = w.iterator(); it.hasNext();)
                {
                    Path pp = it.next();
                    if(isFirst)
                    {
                        isFirst = false;
                        continue;
                    }
                    paths.add(pp);
                }
            }
            catch(Exception e) { continue; }
            try
            {
                for(Path p : paths)
                {
                    String j[] = new String[] {""};
                    BufferedReader reader;
                    if(isJar)
                        reader = new BufferedReader(new InputStreamReader(mod.getClass().getResourceAsStream(p.toString()), StandardCharsets.UTF_8));
                    else
                        reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(p.toUri())), StandardCharsets.UTF_8));
                    reader.lines().forEachOrdered(l -> { if(l != null) j[0] += l.replace(" ", ""); });
                    String json = j[0];
                    MultiBlockInfo mbi = MultiBlockInfo.createFromJsonFile(json, new ResourceLocation(mod.getModId(), p.getFileName().toString().replace(".json", "")));
                    if(mbi != null)
                        register(mbi);
                    else
                        Orion.log.error("Error while trying to read form json file for '" + new ResourceLocation(mod.getModId(), p.getFileName().toString().replace(".json", "")) + "' MultiBlock");
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
