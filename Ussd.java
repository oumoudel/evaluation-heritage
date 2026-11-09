public class Ussd {

    // C1 : Verifier un numero
    public static boolean numeroValide(String n) {
        if (n == null || n.length() != 9) {
            return false;
        }

        String debut = n.substring(0, 2);

        if (!debut.equals("70") &&
            !debut.equals("75") &&
            !debut.equals("76") &&
            !debut.equals("77") &&
            !debut.equals("78")) {
            return false;
        }

        for (int i = 0; i < n.length(); i++) {
            if (n.charAt(i) < '0' || n.charAt(i) > '9') {
                return false;
            }
        }

        return true;
    }

    // C2 : Normaliser un numero
    public static String normaliser(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder resultat = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c != ' ' && c != '.' && c != '-') {
                resultat.append(c);
            }
        }

        String n = resultat.toString();

        if (n.startsWith("+221")) {
            n = n.substring(4);
        } else if (n.startsWith("00221")) {
            n = n.substring(5);
        }

        if (!numeroValide(n)) {
            return null;
        }

        return n;
    }

    // C3 : Masquer un numero
    public static String masquer(String n) {
        if (n == null || n.length() < 4) {
            return null;
        }

        return n.substring(0, 2) + "*****"
                + n.substring(n.length() - 2);
    }

    // C4 : Lire un montant
    public static long lireMontant(String s) {
        if (s == null || s.length() < 1 || s.length() > 9) {
            return -1;
        }

        long montant = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c < '0' || c > '9') {
                return -1;
            }

            montant = montant * 10 + (c - '0');
        }

        return montant;
    }

    // C5 : Formater un montant
    public static String formaterMontant(long m) {
        String nombre = String.valueOf(m);
        StringBuilder resultat = new StringBuilder();

        int debut = 0;

        if (nombre.charAt(0) == '-') {
            debut = 1;
        }

        int compteur = 0;

        for (int i = nombre.length() - 1; i >= debut; i--) {
            resultat.append(nombre.charAt(i));
            compteur++;

            if (compteur == 3 && i > debut) {
                resultat.append(' ');
                compteur = 0;
            }
        }

        if (debut == 1) {
            resultat.append('-');
        }

        return resultat.reverse().toString();
    }

    // C6 : Decouper un code USSD
    public static String[] decouper(String code) {
        if (code == null || code.length() < 4) {
            return null;
        }

        if (code.charAt(0) != '*' ||
            code.charAt(code.length() - 1) != '#') {
            return null;
        }

        int nombreChamps = 1;

        for (int i = 1; i < code.length() - 1; i++) {
            if (code.charAt(i) == '*') {
                if (i == 1 || code.charAt(i - 1) == '*') {
                    return null;
                }
                nombreChamps++;
            }
        }

        if (code.charAt(code.length() - 2) == '*') {
            return null;
        }

        String[] champs = new String[nombreChamps];

        int debut = 1;
        int position = 0;

        for (int i = 1; i < code.length() - 1; i++) {
            if (code.charAt(i) == '*') {
                champs[position] = code.substring(debut, i);
                position++;
                debut = i + 1;
            }
        }

        champs[position] = code.substring(debut, code.length() - 1);

        return champs;
    }
}
    
}
