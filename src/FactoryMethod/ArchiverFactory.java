package FactoryMethod;

public class ArchiverFactory {
    public static Archiver getArchiver(String type) {
        if (type.equalsIgnoreCase("zip")) {
            return new ZipArchiver();
        } else if (type.equalsIgnoreCase("rar")) {
            return new RarArchiver();
        }
        throw new IllegalArgumentException("Unknown archive type: " + type);
    }
}

