public class Caesar {
    private static final char[] RU_LOWER = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray();
    private static final char[] RU_UPPER = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ".toCharArray();
    private static final int ALPH_LEN = RU_LOWER.length;

    public static String shiftText(String text, int key) {
        if (text == null || text.isEmpty()) return text;
        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            sb.append(shiftChar(text.charAt(i), key));
        }
        return sb.toString();
    }

    private static char shiftChar(char ch, int key) {
        int idx;
        idx = indexOf(RU_LOWER, ch);
        if (idx >= 0) {
            int k = normalize(key);
            return RU_LOWER[(idx + k) % ALPH_LEN];
        }
        idx = indexOf(RU_UPPER, ch);
        if (idx >= 0) {
            int k = normalize(key);
            return RU_UPPER[(idx + k) % ALPH_LEN];
        }
        return ch;
    }

    private static int indexOf(char[] arr, char ch) {
        for (int i = 0; i < arr.length; i++) if (arr[i] == ch) return i;
        return -1;
    }

    private static int normalize(int key) {
        int k = key % ALPH_LEN;
        if (k < 0) k += ALPH_LEN;
        return k;
    }
}
