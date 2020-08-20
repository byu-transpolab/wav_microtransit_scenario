package org.matsim.project;

import byu.edu.activitysimutils.ActivitySimFacilitiesReader;
import byu.edu.activitysimutils.ActivitySimPersonsReader;
import byu.edu.activitysimutils.ActivitySimTripsReader;
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.PopulationWriter;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;

import java.io.File;

public class InputFilesReader {
    private static final Logger log = Logger.getLogger(InputFilesReader.class);
    private Scenario scenario;

    public InputFilesReader(Scenario sc){
        this.scenario = sc;
    }

    /**
     * Read the ActivitySim output files into the scenario
     * @param personsFile Path to activitysim output persons file
     * @param tripsFile Path to activitysim output trips file
     */
    public void readActivitySimFiles(File personsFile, File tripsFile, File facilitiesFile){
        ActivitySimFacilitiesReader facilitiesReader = new ActivitySimFacilitiesReader(scenario, facilitiesFile);
        facilitiesReader.readFacilities();
        ActivitySimPersonsReader personsReader = new ActivitySimPersonsReader(scenario, personsFile);
        personsReader.readPersons();
        ActivitySimTripsReader tripsReader = new ActivitySimTripsReader(scenario, tripsFile);
        tripsReader.readTrips();

    }


    public static void main(String[] args){
        Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        InputFilesReader reader = new InputFilesReader(scenario);

        File personsFile = new File("data/final_persons.csv");
        File tripsFile = new File("data/final_trips.csv");
        File facilitiesFile = new File("data/facility_ids.csv");
        reader.readActivitySimFiles(personsFile, tripsFile, facilitiesFile);

        new PopulationWriter(scenario.getPopulation()).write("data/final_population.xml.gz");
    }

}
