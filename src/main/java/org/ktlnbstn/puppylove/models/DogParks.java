package org.ktlnbstn.puppylove.models;

public enum DogParks {

        BLUE_DOG_POND("Blue Dog Park"),
        DENNY_PARK("Denny Park"),
        DR_JOSE_RIZAL_PARK("Dr. Jose Rizal Park"),
        GENESEE_PARK("Genesee Park"),
        GOLDEN_GARDENS("Golden Gardens"),
        I5_COLONNADE("I5 Colonnade"),
        KINNEAR_PARK("Kinnear Park"),
        MAGNOLIA_MANOR_PARK("Magnolia Manor Park"),
        MAGNUSON_PARK("Magnuson Park"),
        NORTHACRES_PARK("Northacres Park"),
        PLYMOUTH_PILLARS("Plymouth Pillars"),
        REGRADE_PARK("Regrade Park"),
        WESTCREST_PARK("Westcrest Park"),
        WOODLAND_PARK("Woodland Park"),
        ALL("All Puppy Owners");

        private String dogParkName;
        private DogParks(String dogParkName){
                this.dogParkName = dogParkName;
        }

        @Override
        public String toString() {
                return dogParkName;
        }
}
