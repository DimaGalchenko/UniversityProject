package view.component.label;

public abstract class ComponentNameHelper {
    private static int lastConnectionSerialNumber = 0;
    private static char lastComponentNodeWord = 64;
    private static int lastFixedComponentSerialNumber = 0;

    public static String generateComponentNameForConnection() {
        return String.valueOf(++lastConnectionSerialNumber);
    }

    public static String generateSerialNameForNode() {
        return String.valueOf(++lastComponentNodeWord);
    }

    public static String generateFixedComponentName() {
        lastFixedComponentSerialNumber++;
        return "O" + lastFixedComponentSerialNumber;
    }
}
