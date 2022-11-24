namespace PyxisKapriBack.Constants
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
        public const string IMAGE_PATH = @"\Images\DefaultProfileImage\";
        public const string IMAGE_PATH_LINUX = @"/Images/DefaultProfileImage/";

        // Posts
        public const int FIRST_PAGE = 1;
        public const int NUMBER_OF_POSTS = 5;

        #region 'Error Messages'
        public const string resNoFoundCountry       = "Država nije pronađena";
        public const string resNoFoundCity          = "Grad nije pronađen";
        public const string resNoFoundLocation      = "Lokacija nije pronađena";
        public const string resDeleteFailed         = "Neuspešno brisanje {0}";
        public const string resNullValue            = "Poslata je NULL vrednost za argument";
        public const string resNoFoundComment       = "Nije pronađen komentar";
        public const string resNoFoundUser          = "Nije pronađen korisnik";
        public const string resCommentDislikeExists = "Dislike već postoji";
        public const string resNoFoundPost          = "Post nije pronađen";
        public const string resDeleteLikeFailed     = "Neuspešno brisanje like-a";
        public const string resDeleteDislikeFailed  = "Neuspešno brisanje dislike-a";
        public const string resPermissionDenied    = "Odbijen pristup!"; 
        #endregion

        #region 'Ok message'
        public const string resDeletedDislike = "Dislike je uspešno obrisan!";
        public const string resDeletedLike    = "Like je uspešno obrisan!";
        #endregion

        public const int TAKE_ELEMENT = 5;
        public const double DISTANCE = 1500;
        // Comment status

        // Images 
        public const string ROOT_FOLDER = "Images";
        public const string DEFAULT_IMAGE_PATH = "DefaultProfileImage";
        public const string DEFAULT_IMAGE_NAME = "default-image.png";
        public const string POST_NAME = "Post";
    }
    public enum CommentState
    {
        DISLIKED = -1,
        NONE = 0,
        LIKED = 1
    }
    public enum SearchType
    {
        LOCATION, 
        COORDINATES, 
        CITY, 
        COUNTRY
    }
}
