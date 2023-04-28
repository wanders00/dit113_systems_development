package com.group6.locusimperium;

public enum ValueSubtopic {
    PEOPLE_COUNT("peopleCount"),
    TEMPERATURE_VALUE("temperatureValue"),
    HUMIDITY_VALUE("humidityValue"),
    LOUDNESS_VALUE("loudnessValue"),
    MAX_PEOPLE_COUNT("maxPeopleCount"),
    MAX_TEMPERATURE("maxTemperature"),
    MAX_HUMIDITY("maxHumidity"),
    MAX_LOUDNESS("maxLoudness");

    private final String subtopic;

    ValueSubtopic(String subtopicArg) {
        this.subtopic = subtopicArg;
    }

    /**
     * Inputs a topic as a String and loops through all ValueSubtopic and returns it if it's subtopic equals the input.
     *
     * @param inputTopic The topic to input to the method
     * @return Corresponding ValueSubtopic object, if none, null.
     */
    public static ValueSubtopic whichSubTopic(String inputTopic) {
        for (ValueSubtopic currentSubtopic : ValueSubtopic.values()) {
            if (currentSubtopic.getSubtopic().equals(inputTopic)) {
                return currentSubtopic;
            }
        }
        return null;
    }

    /**
     * Returns the total topic of the ValueSubtopic which is a combination of the super topic and the object subtopic.
     * See BrokerConnection.SUBSCRIPTION_TOPIC attribute for super topic.
     *
     * @return Corresponding ValueSubtopics subtopic with the super topic before it.
     * @see BrokerConnection
     */
    public String getSubtopic() {
        return BrokerConnection.SUPER_SUBSCRIPTION_TOPIC + this.subtopic;
    }
}