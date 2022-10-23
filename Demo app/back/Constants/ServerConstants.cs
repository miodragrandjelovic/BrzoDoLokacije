namespace PyxisKapriBack.Constants
{
    public static class ServerConstants
    {
        const string SERVER = "localhost";
        const string DATABASE = "PyxisKapriProduction"; 
        const string USERNAME = "root";
        const string PASSWORD = "";
        const string PORT = "3306";

        public static String CONN_STRING = String.Concat("server=" + SERVER + "; port=" + PORT +
                                                         "; database=" + DATABASE + "; user=" + USERNAME +
                                                         "; password=" + ";"); 
    }
}
