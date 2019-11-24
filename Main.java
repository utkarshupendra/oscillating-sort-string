import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        System.out.println(getOscillatingString(args[0]));
    }

    public static String getOscillatingString(String str) {
        List<Character> charList = str.chars().mapToObj(it -> (char) it).sorted().collect(Collectors.toList());
        Map<Character, Integer> characterCountMap = new HashMap<>();
        char prevChar = Character.MIN_VALUE;
        StringBuilder output = new StringBuilder();

        for (char ch : charList) {
            if (!characterCountMap.containsKey(ch)) {
                characterCountMap.put(ch, 1);
            } else {
                characterCountMap.put(ch, characterCountMap.get(ch)+1);
            }
        }

        List<Character> ascendingSortedChars = getKeysInAscendingOrder(characterCountMap);
        List<Character> descendingSortedChars = getKeysInDescendingOrder(characterCountMap);

        boolean increaseToggle = false;
        while (!checkIfEntrySetIsEmpty(characterCountMap.entrySet())) {
            increaseToggle = !increaseToggle;
            if (characterCountMap.size() == 0)
                break;
            Iterator iter = increaseToggle ? ascendingSortedChars.iterator() : descendingSortedChars.iterator();
            while (iter.hasNext()) {
                char ch = (Character) iter.next();
                if (characterCountMap != null && increaseToggle && ch > prevChar && characterCountMap.containsKey(ch)) {
                    output.append(ch);
                    if (characterCountMap.get(ch) == 1) {
                        characterCountMap.remove(ch);
                    } else {
                        characterCountMap.put(ch, characterCountMap.get(ch) - 1);
                    }
                    prevChar = ch;
                } else if (characterCountMap != null && !increaseToggle && ch < prevChar && characterCountMap.containsKey(ch)) {
                    output.append(ch);
                    if (characterCountMap.get(ch) == 1) {
                        characterCountMap.remove(ch);
                    } else {
                        characterCountMap.put(ch, characterCountMap.get(ch) - 1);
                    }
                    prevChar = ch;
                } else if (characterCountMap.size() == 1) {
                    char key = (char) characterCountMap.keySet().toArray()[0];
                    for (int i = 0; i < characterCountMap.get(key); i++) {
                        output.append(key);
                    }
                    characterCountMap.remove(key);
                }
            }
        }

        return String.valueOf(output);
    }

    private static List getKeysInDescendingOrder(Map<Character, Integer> characterCountMap) {
        List orderedList = new ArrayList(characterCountMap.keySet());
        orderedList.sort(Collections.reverseOrder());
        return orderedList;
    }

    private static List<Character> getKeysInAscendingOrder(Map<Character, Integer> characterCountMap) {
        return characterCountMap.keySet().stream().sorted().collect(Collectors.toList());
    }



    private static boolean checkIfEntrySetIsEmpty(Set<Map.Entry<Character, Integer>> set) {
        for (Map.Entry<Character, Integer> i : set) {
            if (i.getValue() > 0) {
                return false;
            }
        }
        return true;
    }
}
