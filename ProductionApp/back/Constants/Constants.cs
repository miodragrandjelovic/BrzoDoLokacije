﻿namespace PyxisKapriBack.Constants
{
    public static class Constants
    {
        public const String USER_PATH = "";
        public const String UNKNWOWN = "Nepoznato";

        // JWT Constants

        public const string FIRST_NAME = "FirstName";
        public const string LAST_NAME = "LastName";

        // Roles Constants 
        public const string ADMIN     = "Admin"; 
        public const string USER      = "User";
        public const string MODERATOR = "Moderator";
        public const int ALL_ROLES = 0;


        // DefaultImage path
        public const string IMAGE_PATH = @"..\back\Images\DefaultProfileImage\";
        #region 'Error Messages'
        public const string resNoFoundCountry  = "Država nije pronađena";
        public const string resNoFoundCity     = "Grad nije pronađen";
        public const string resNoFoundLocation = "Lokacija nije pronađena";
        public const string resDeleteFailed    = "Neuspešno brisanje {0}";
        #endregion

        public const int TAKE_ELEMENT = 5; 

    }
}
