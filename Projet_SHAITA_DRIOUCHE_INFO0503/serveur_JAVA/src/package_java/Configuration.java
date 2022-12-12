package package_java;

public class Configuration {

    private final String nomFichier;
    private org.json.JSONObject config;

    public Configuration(String nomFichier) {
        this.nomFichier = nomFichier;
        charger();
    }

    public Configuration(String nomFichier, boolean creation) {
        if (!creation) {
            this.nomFichier = nomFichier;
            charger();
        } else {
            this.nomFichier = nomFichier;
            config = new org.json.JSONObject();
        }
    }

    public static boolean fichierExiste(String nomFichier) {
        return new java.io.File(nomFichier).exists();
    }

    public String getString(String clef) {
        return config.getString(clef);
    }

    public int getInt(String clef) {
        return config.getInt(clef);
    }

    public void ajouterValeur(String clef, int valeur) {
        config.put(clef, valeur);
    }

    public void ajouterValeur(String clef, String valeur) {
        config.put(clef, valeur);
    }

    private void charger() {
        String json = null;
        try {
            json = new String(java.nio.file.Files.readAllBytes(new java.io.File(nomFichier).toPath()));
            config = new org.json.JSONObject(json);
        } catch (java.io.IOException ioe) {
            System.err.println("An I/O error occurs reading from the stream");
            System.err.println(ioe);
            System.exit(110);
        } catch (OutOfMemoryError ome) {
            System.err.println(
                    "An array of the required size cannot be allocated, for example the file is larger that 2GB");
            System.err.println(ome);
            System.exit(120);
        } catch (SecurityException se) {
            System.err.println(
                    "In the case of the default provider, and a security manager is installed, the checkRead method is invoked to check read access to the file.");
            System.err.println(se);
            System.exit(130);
        }
    }

    /**
     * Sauvegarde la configuration actuelle dans le fichier indiqué par
     * <em>nomFichier</em>.
     */
    public void sauvegarder() {
        try {
            java.nio.file.Files.write(new java.io.File(nomFichier).toPath(), config.toString().getBytes());
        } catch (IllegalArgumentException iae) {
            System.err.println("Options contains an invalid combination of options...");
            System.err.println(iae);
            System.exit(10);
        } catch (java.io.IOException ioe) {
            System.err.println("An I/O error occurs writing to or creating the file");
            System.err.println(ioe);
            System.exit(20);
        } catch (UnsupportedOperationException uoe) {
            System.err.println("An unsupported option is specified");
            System.err.println(uoe);
            System.exit(30);
        } catch (SecurityException se) {
            System.err.println(
                    "In the case of the default provider, and a security manager is installed, the checkWrite method is invoked to check write access to the file. The checkDelete method is invoked to check delete access if the file is opened with the DELETE_ON_CLOSE option.An unsupported option is specified");
            System.err.println(se);
            System.exit(40);
        }
    }
}
