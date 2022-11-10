namespace PyxisKapriBack.Models
{
    public class User
    {
        [Key]
        public int Id { get; set; }

        [MaxLength(50)]
        public string Username { get; set; } = string.Empty;
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public byte[] Password { get; set; }
        public byte[] PasswordKey { get; set; }

        public string Email { get; set; } = string.Empty;
        public byte[]? ProfileImage { get; set; }
        public List<Post>? Posts { get; set; }
        public List<Like>? Likes { get; set; }
        public List<Dislike>? Dislikes { get; set; }
        public List<Comment>? Comments { get; set; }

        public List<Follow>? Followers { get; set; } // lista korisnika koji prate ovog korisnika 
        /*
         * Na primer, Ja pratim oficijalnu stranu Jaffa Crvenke 
         */

        public List<Follow>? Following { get; set; } // lista korisnika koje prati ovaj korisnik
        /*
         * Na primer, Jaffa Crvenka prati Stark 
         */
        #region 'Country'

        [ForeignKey("Country")]
        public int? CountryId { get; set; } = Constants.ModelConstants.NULL_REFERENCE;

        public Country Country { get; set; }

        #endregion

        #region 'Role'

        [ForeignKey("Role")]
        public int? RoleId { get; set; } = Constants.ModelConstants.NULL_REFERENCE;

        public Role Role { get; set; }

        #endregion
    }
}
