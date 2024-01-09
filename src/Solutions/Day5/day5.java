package Solutions.Day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class day5 {

    private record EntityMappingRange(long range, long destinationStart) {}
    public void solveExample() {
        Scanner inStream = null;
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resources/day5_part1"));
            inStream = new Scanner(br);

            inStream.useDelimiter("\n\n");

            ArrayList<Long> seeds = parseSeedInfo(inStream.next());
            TreeMap<Long, EntityMappingRange> seedSoilMap = parseMapFromData(inStream.next());
            TreeMap<Long, EntityMappingRange> soilFertilizerMap = parseMapFromData(inStream.next());
            TreeMap<Long, EntityMappingRange> fertilizerWaterMap = parseMapFromData(inStream.next());
            TreeMap<Long, EntityMappingRange> waterLightMap = parseMapFromData(inStream.next());
            TreeMap<Long, EntityMappingRange> lightTemperatureMap = parseMapFromData(inStream.next());
            TreeMap<Long, EntityMappingRange> temperatureHumidityMap = parseMapFromData(inStream.next());
            TreeMap<Long, EntityMappingRange> humidityLocationMap = parseMapFromData(inStream.next());

            ArrayList<Long> soilLocations  = mapSourceToDestination(seeds, seedSoilMap);
            ArrayList<Long> fertilizerLocations  = mapSourceToDestination(soilLocations, soilFertilizerMap);
            ArrayList<Long> waterLocations  = mapSourceToDestination(fertilizerLocations, fertilizerWaterMap);
            ArrayList<Long> lightLocations  = mapSourceToDestination(waterLocations, waterLightMap);
            ArrayList<Long> temperatureLocations  = mapSourceToDestination(lightLocations, lightTemperatureMap);
            ArrayList<Long> humidityLocations  = mapSourceToDestination(temperatureLocations, temperatureHumidityMap);
            ArrayList<Long> finalLocations  = mapSourceToDestination(humidityLocations, humidityLocationMap);

            Collections.sort(finalLocations);
            System.out.println("Min location: " + finalLocations.get(0));

        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        } finally {
            if (inStream != null)
                inStream.close();
        }
    }

//    ABANDONED
    public void solvePart2() {
        Scanner inStream = null;
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resources/day5_example"));
            inStream = new Scanner(br);

            inStream.useDelimiter("\n\n");

            TreeMap<Long, Long> seedsRange = parseSeedRange(inStream.next());

            TreeMap<Long, EntityMappingRange> seedSoilMap = parseMapFromData(inStream.next());
            TreeMap<Long, EntityMappingRange> soilFertilizerMap = parseMapFromData(inStream.next());
            TreeMap<Long, EntityMappingRange> fertilizerWaterMap = parseMapFromData(inStream.next());
            TreeMap<Long, EntityMappingRange> waterLightMap = parseMapFromData(inStream.next());
            TreeMap<Long, EntityMappingRange> lightTemperatureMap = parseMapFromData(inStream.next());
            TreeMap<Long, EntityMappingRange> temperatureHumidityMap = parseMapFromData(inStream.next());
            TreeMap<Long, EntityMappingRange> humidityLocationMap = parseMapFromData(inStream.next());

            TreeMap<Long, Long> soilRanges  = mapRanges(seedsRange, seedSoilMap);
//            ArrayList<Long> fertilizerLocations  = mapSourceToDestination(soilLocations, soilFertilizerMap);
//            ArrayList<Long> waterLocations  = mapSourceToDestination(fertilizerLocations, fertilizerWaterMap);
//            ArrayList<Long> lightLocations  = mapSourceToDestination(waterLocations, waterLightMap);
//            ArrayList<Long> temperatureLocations  = mapSourceToDestination(lightLocations, lightTemperatureMap);
//            ArrayList<Long> humidityLocations  = mapSourceToDestination(temperatureLocations, temperatureHumidityMap);
//            ArrayList<Long> finalLocations  = mapSourceToDestination(humidityLocations, humidityLocationMap);

//            Collections.sort(finalLocations);
//            System.out.println("Min location: " + finalLocations.get(0));

        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        } finally {
            if (inStream != null)
                inStream.close();
        }
    }

    private TreeMap<Long, Long> mapRanges(TreeMap<Long, Long> seedsRange, TreeMap<Long, EntityMappingRange> seedSoilMap) {
        TreeMap<Long, Long> mappedRanges = new TreeMap<>();
        for (Map.Entry<Long, Long> range: seedsRange.entrySet()) {
//            create alias for readability
            Long rangeStart = range.getKey();
            Long rangeEnd = range.getValue();

            Long floorKey = seedSoilMap.floorKey(rangeStart);
            Long lowestKey = floorKey == null ? rangeStart : floorKey;

            Long ceilingKey = seedSoilMap.ceilingKey(rangeEnd);
            Long highestKey = ceilingKey == null ? rangeEnd : ceilingKey;

            Map<Long, EntityMappingRange> subMap = seedSoilMap.subMap(lowestKey, highestKey);
            for (Map.Entry<Long, EntityMappingRange> mapperRange: subMap.entrySet()) {
//                alias for readability
                Long mapperRangeStart = mapperRange.getKey();
                Long mapperRangeEnd = mapperRangeStart + mapperRange.getValue().range;



            }
            System.out.println(subMap);
        }
        return mappedRanges;
    }

    private TreeMap<Long, Long> parseSeedRange(String seedsInfoStr) {
        TreeMap<Long, Long> seedRange = new TreeMap<>();
        String[] seedsStr = (
                seedsInfoStr.split(": ")[1]
        ).split(" ");
        for (int idx=0; idx< seedsStr.length; idx+=2) {
            long rangeStart = Long.parseLong(seedsStr[idx]);
            long rangeEnd = rangeStart + Long.parseLong(seedsStr[idx+1]);
            seedRange.put(
                    rangeStart,
                    rangeEnd
            );
        }
        return seedRange;
    }

    private static boolean isSourceWithinRange(Map.Entry<Long, EntityMappingRange> entry, long sourceKey) {
        long rangeStart = entry.getKey();
        long rangeEnd = entry.getKey() + entry.getValue().range;
        return sourceKey >= rangeStart && sourceKey < rangeEnd;
    }

    private ArrayList<Long> mapSourceToDestination(ArrayList<Long> sourceList, TreeMap<Long, EntityMappingRange> destinationMapping) {
        // sort source list
        Collections.sort(sourceList);
        ArrayList<Long> destinationList = new ArrayList<>();

        for (long source: sourceList) {
            Map.Entry<Long, EntityMappingRange> destinationEntry = destinationMapping.floorEntry(source);
            if (destinationEntry != null && isSourceWithinRange(destinationEntry, source)) {
                long diff = source - destinationEntry.getKey();
                long destination = destinationEntry.getValue().destinationStart + diff;
                destinationList.add(destination);
            } else {
                destinationList.add(source);
            }
        }

        return destinationList;
    }

    private TreeMap<Long, EntityMappingRange> parseMapFromData(String data) {
        String[] dataSplit = data.split("\n");
        TreeMap<Long, EntityMappingRange> parsedMap = new TreeMap<>();

        for (int idx = 1; idx<dataSplit.length; idx++) {
            String[] numbersStr = dataSplit[idx].split(" ");
            parsedMap.put(
                    Long.parseLong(numbersStr[1]),
                    new EntityMappingRange(
                            Long.parseLong(numbersStr[2]),
                            Long.parseLong(numbersStr[0])
                    )
            );

        }
        return parsedMap;
    }

    private ArrayList<Long> parseSeedInfo(String seedsInfoStr) {
        String[] seedsStr = (
                seedsInfoStr.split(": ")[1]
        ).split(" ");
        List<Long> seedsList = Arrays.stream(seedsStr)
                .map(Long::parseLong)
                .toList();
        return new ArrayList<>(seedsList);
    }
}
