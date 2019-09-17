/* amodeus - Copyright (c) 2018, ETH Zurich, Institute for Dynamic Systems and Control */
package amod.scenario;

import java.io.File;
import java.time.LocalDate;

import org.matsim.core.network.FixedIntervalTimeVariantLinkFactory;

import amod.scenario.fleetconvert.TripFleetConverter;
import ch.ethz.idsc.amodeus.util.AmodeusTimeConvert;
import ch.ethz.idsc.amodeus.util.math.GlobalAssert;

public class Scenario {

    public static File create(File dataDir, File tripFile, //
            TripFleetConverter converter, //
            File workingDirectory, File processingDir, //
            LocalDate simulationDate, //
            AmodeusTimeConvert timeConvert) throws Exception {
        Scenario creator = new Scenario(dataDir, tripFile, converter, //
                workingDirectory, processingDir, simulationDate, timeConvert);
        creator.run();
        return creator.finalTripsFile;

    }

    // --

    private final File dataDir;
    private final File tripFile;
    private final File processingDir;
    private final LocalDate simulationDate;
    private final AmodeusTimeConvert timeConvert;
    public final TripFleetConverter fleetConverter;
    private File finalTripsFile = null;

    private Scenario(File dataDir, File tripFile, //
            TripFleetConverter converter, //
            File workingDirectory, File processingDir, //
            LocalDate simulationDate, //
            AmodeusTimeConvert timeConvert) throws Exception {
        GlobalAssert.that(dataDir.isDirectory());
        GlobalAssert.that(tripFile.exists());
        this.dataDir = dataDir;
        this.tripFile = tripFile;
        this.processingDir = processingDir;
        this.simulationDate = simulationDate;
        this.timeConvert = timeConvert;
        this.fleetConverter = converter;
    }

    private void run() throws Exception {
        InitialFiles.copyToDir(processingDir, dataDir);
        fleetConverter.setFilters();
        fleetConverter.run(processingDir, tripFile, simulationDate, timeConvert);
        finalTripsFile = fleetConverter.getFinalTripFile();
    }

}
