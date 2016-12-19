import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class ProteinParser {

    Sequence parse(String pattern) {
        if (pattern.isEmpty()) {
            throw new RuntimeException("Pattern cannot be empty");
        }
        List<String> patternElements = SplitPattern(pattern);
        Collection<ProteinEater> proteinEaters = new ArrayList<>();
        Sequence sequence = new Sequence(proteinEaters);

        for (String patternElement : patternElements) {
            if (patternElement.contains("(") && !(patternElement.contains(","))) {
                String repetitionString = patternElement.substring(2, patternElement.length() - 1);

                ProteinEater newProteinEater = parseSingleAcid(patternElement);
                proteinEaters.add(new RepeatedEater(
                                newProteinEater,
                                Integer.parseInt(repetitionString)
                        )
                );
            } else if (patternElement.contains("[")) {
                Collection<ProteinEater> acidEaters = new ArrayList<>();
                for (int i = 0; i < patternElement.length(); i++) {
                    acidEaters.add(new AcidEater(patternElement.charAt(i)));
                }
                proteinEaters.add(new WhiteListEater(acidEaters));
            } else if (patternElement.contains("{")) {
                Collection<ProteinEater> acidEaters = new ArrayList<>();
                for (int i = 0; i < patternElement.length(); i++) {
                    acidEaters.add(new AcidEater(patternElement.charAt(i)));
                }
                proteinEaters.add(new BlackListEater(acidEaters));
            } else if (patternElement.contains("(") && patternElement.contains(",")) {
                String rangeString = patternElement.substring(2, patternElement.length() - 1);
                List<String> ranges = Arrays.asList(rangeString.split(","));
                ProteinEater newProteinEater = parseSingleAcid(patternElement);

                proteinEaters.add(new RangeEater(
                        newProteinEater,
                        Integer.parseInt(ranges.get(0)),
                        Integer.parseInt(ranges.get(1))
                ));
            } else {
                proteinEaters.add(parseSingleAcid(patternElement));
            }
        }
        return sequence;
    }

    private ProteinEater parseSingleAcid(String patternElement) {
        String acid = patternElement.substring(0, 1);
        if (acid.startsWith("x")) {
            return new WildcardEater();
        } else {
            return new AcidEater(acid.charAt(0));
        }
    }


    private List<String> SplitPattern(String pattern) {
        List<String> patternElements = new ArrayList<>();
        if (pattern.contains("-")) {
            patternElements = Arrays.asList(pattern.split("-"));
        } else {
            patternElements.add(pattern);
        }
        return patternElements;

    }

}