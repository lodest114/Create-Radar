package com.happysg.radar.ponder;

import com.happysg.radar.registry.ModBlocks;
import com.simibubi.create.foundation.ponder.*;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ProcessingScenes {
    public static void radarContraption(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("radar_contraption", "Creating a Radar!");
        scene.rotateCameraY(180);
        scene.configureBasePlate(0, 0, 5);
        scene.world.showSection(util.select.layer(0), Direction.DOWN);
        scene.world.showSection(util.select.layer(1), Direction.DOWN);
        scene.idle(40);
        BlockPos bearing = util.grid.at(2, 2, 2);
        scene.world.showSection(util.select.position(bearing), Direction.DOWN);
        Vec3 bearingSide = util.vector.blockSurface(bearing, Direction.EAST);

        scene.overlay.showText(40)
                .pointAt(bearingSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Place Radar Bearing");
        scene.idle(60);

        BlockPos receiverPos = util.grid.at(2, 3, 2);
        ElementLink<WorldSectionElement> receiver =
                scene.world.showIndependentSection(util.select.position(receiverPos), Direction.DOWN, 15);
        Vec3 receiverSide = util.vector.blockSurface(receiverPos, Direction.EAST);

        scene.overlay.showText(40)
                .pointAt(receiverSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Place Radar Receiver");
        scene.idle(40);

        BlockPos dish1 = util.grid.at(3, 3, 2);
        BlockPos dish2 = util.grid.at(1, 3, 2);
        ElementLink<WorldSectionElement> simple_dishes =
                scene.world.showIndependentSection(util.select.position(dish1).add(util.select.position(dish2)), Direction.DOWN, 15);
        Vec3 dishSide = util.vector.blockSurface(dish1, Direction.EAST);
        scene.overlay.showText(40)
                .pointAt(dishSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Add Radar Plates");
        scene.idle(50);


        scene.world.replaceBlocks(util.select.position(dish1), ModBlocks.RADAR_DISH_BLOCK.get().defaultBlockState(), true);
        scene.world.replaceBlocks(util.select.position(dish2), ModBlocks.RADAR_DISH_BLOCK.get().defaultBlockState(), true);
        scene.overlay.showText(40)
                .pointAt(dishSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Radar Dishes can be used interchangeably with plates");
        scene.idle(50);

        ElementLink<WorldSectionElement> large_dishes =
                scene.world.showIndependentSection(util.select.layer(4), Direction.DOWN, 15);
        scene.overlay.showText(40)
                .pointAt(dishSide.add(0, 1, 0))
                .placeNearTarget()
                .attachKeyFrame()
                .text("Additional dishes/plates extend range");
        scene.idle(40);


        scene.overlay.showText(40)
                .pointAt(bearingSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Power Radar Bearing");

        scene.idle(10);
        scene.world.rotateBearing(bearing, 360, 200);
        scene.world.rotateSection(receiver, 0, 360, 0, 200);
        scene.world.rotateSection(simple_dishes, 0, 360, 0, 200);
        scene.world.rotateSection(large_dishes, 0, 360, 0, 200);
        scene.world.setKineticSpeed(util.select.layer(1), 32);
        scene.idle(100);
        scene.markAsFinished();
    }

    public static void radarLinking(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("radar_linking", "Linking a Radar to a Monitor!");
        scene.rotateCameraY(180);
        scene.configureBasePlate(0, 0, 5);
        scene.world.showSection(util.select.layer(0), Direction.DOWN);
        scene.idle(40);

        Selection monitor = util.select.fromTo(0, 1, 3, 0, 2, 4);
        Vec3 monitorSide = util.vector.blockSurface(new BlockPos(0, 1, 3), Direction.EAST);
        scene.world.showSection(monitor, Direction.DOWN);
        scene.overlay.showText(40)
                .pointAt(monitorSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Place Monitor");
        scene.idle(60);

        scene.world.showSection(util.select.position(2, 1, 2), Direction.DOWN);
        BlockPos bearing = util.grid.at(2, 2, 2);
        scene.world.showSection(util.select.position(bearing), Direction.DOWN);
        ElementLink<WorldSectionElement> large_dishes =
                scene.world.showIndependentSection(util.select.layers(3, 3), Direction.DOWN, 15);
        Vec3 bearingSide = util.vector.blockSurface(bearing, Direction.EAST);

        scene.overlay.showText(40)
                .pointAt(bearingSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Build Radar Contraption");
        scene.idle(60);
        BlockPos link = util.grid.at(3, 2, 2);
        scene.world.showSection(util.select.position(link), Direction.WEST);
        scene.idle(15);
        scene.overlay.chaseBoundingBoxOutline(PonderPalette.INPUT, monitor, new AABB(link).contract(.5f, 0, 0), 60);
        scene.idle(5);
        scene.overlay.chaseBoundingBoxOutline(PonderPalette.OUTPUT, link, new AABB(new BlockPos(0, 1, 3)).expandTowards(0, 1, 1), 60);
        scene.overlay.showText(40)
                .text("Link using Display Links")
                .pointAt(bearingSide)
                .attachKeyFrame()
                .colored(PonderPalette.OUTPUT)
                .placeNearTarget();
        scene.idle(50);

        scene.overlay.showText(40)
                .text("Right click Display Link to activate")
                .pointAt(bearingSide)
                .attachKeyFrame()
                .placeNearTarget();

        scene.idle(40);
        scene.markAsFinished();
    }

}
