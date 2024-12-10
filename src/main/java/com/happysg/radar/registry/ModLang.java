package com.happysg.radar.registry;

import com.happysg.radar.CreateRadar;

import static com.happysg.radar.CreateRadar.REGISTRATE;

public class ModLang {

    public static void register() {
        CreateRadar.getLogger().info("Registering Lang!");
        REGISTRATE.addRawLang(CreateRadar.MODID + ".ponder.radar_contraption.header", "Creating a Radar!");
        REGISTRATE.addRawLang(CreateRadar.MODID + ".ponder.radar_contraption.text_1", "Place Radar Bearing");
        REGISTRATE.addRawLang(CreateRadar.MODID + ".ponder.radar_contraption.text_2", "Place Radar Receiver");
        REGISTRATE.addRawLang(CreateRadar.MODID + ".ponder.radar_contraption.text_3", "Add Radar Dishes");
        REGISTRATE.addRawLang(CreateRadar.MODID + ".ponder.radar_contraption.text_4", "Radar Dishes can be used interchangeably with Radar Plates");
        REGISTRATE.addRawLang(CreateRadar.MODID + ".ponder.radar_contraption.text_5", "Additional dishes/plates extend range");
        REGISTRATE.addRawLang(CreateRadar.MODID + ".ponder.radar_contraption.text_6", "Power Radar Bearing");

        REGISTRATE.addRawLang(CreateRadar.MODID + ".ponder.tag.radar_components", "Radars");
        REGISTRATE.addRawLang(CreateRadar.MODID + ".ponder.tag.radar_components.description", "Components which allow the creation of Radar Tracking System");

        REGISTRATE.addRawLang(CreateRadar.MODID + ".ponder.radar_linking.header", "Linking a Radar to a Monitor!");
        REGISTRATE.addRawLang(CreateRadar.MODID + ".ponder.radar_linking.text_1", "Place Monitor");
        REGISTRATE.addRawLang(CreateRadar.MODID + ".ponder.radar_linking.text_2", "Build Radar Contraption");
        REGISTRATE.addRawLang(CreateRadar.MODID + ".ponder.radar_linking.text_3", "Link using Display Link");
        REGISTRATE.addRawLang(CreateRadar.MODID + ".ponder.radar_linking.text_4", "Right click Display Link to activate");

    }
}
