package me.nanorasmus.nanoutils.structures;


import me.nanorasmus.nanoutils.FileUtils;

import java.util.HashMap;

public class Structures {
    static String folder = "Structures/";

    static HashMap<String, Structure> structures = new HashMap<>();

    public static void RegisterStructure(String id, String structureJson) {
        Structure structure = FileUtils.gson.fromJson(structureJson, Structure.class);
        FileUtils.SoftSave(folder + id + ".json", structure);
        structures.put(id, structure);
    }

    public static Structure GetStructure(String id) {
        if (structures.containsKey(id)) {
            return structures.get(id);
        } else if (FileUtils.Exists(folder + id + ".json")) {
            Structure structure = FileUtils.Load(folder + id + ".json", new Structure());
            structures.put(id, structure);
            return structure;
        } else {
            throw new RuntimeException(new Exception(
                    "Tried to load non-existent structure '" + id + "'"
            ));
        }
    }

    private static String PrettifyStructureJson(String json) {
        Structure middle = FileUtils.gson.fromJson(json, Structure.class);
        return FileUtils.gson.toJson(middle);
    }
}
