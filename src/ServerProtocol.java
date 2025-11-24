public class ServerProtocol {

    public static Object processInput(Object object) {
        if (checkIfValid(object)) {
            if (object instanceof Question) {
                return (Question) object;
            }
        }
        return null;
    }
    private static boolean checkIfValid(Object object){
        if (object == null){
            return false;
        }
        else {
            if (!(object instanceof Question)){
                return false;
            }
        }
        return true;
    }

}
