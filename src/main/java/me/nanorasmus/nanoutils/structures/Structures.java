package me.nanorasmus.nanoutils.structures;


import me.nanorasmus.nanoutils.data.file.JSONFileHelper;

import java.util.HashMap;

public class Structures {
    static String folder = "Structures/";

    static HashMap<String, Structure> structures = new HashMap<>();

    public static void RegisterStructure(String id, String structureJson) {
        Structure structure = JSONFileHelper.gson.fromJson(structureJson, Structure.class);
        JSONFileHelper.SoftSave(folder + id + ".json", structure);
        structures.put(id, structure);
    }

    public static Structure GetStructure(String id) {
        if (structures.containsKey(id)) {
            return structures.get(id);
        } else if (JSONFileHelper.Exists(folder + id + ".json")) {
            Structure structure = JSONFileHelper.Load(folder + id + ".json", new Structure());
            structures.put(id, structure);
            return structure;
        } else {
            throw new RuntimeException(new Exception(
                    "Tried to load non-existent structure '" + id + "'"
            ));
        }
    }

    private static String PrettifyStructureJson(String json) {
        Structure middle = JSONFileHelper.gson.fromJson(json, Structure.class);
        return JSONFileHelper.gson.toJson(middle);
    }
}
