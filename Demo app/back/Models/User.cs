namespace PyxisKapriBack.Models
{
    public class User
    {
        [Key]
        public int Id { get; set; }

        [MaxLength(50)]
        public string Username { get; set; } = string.Empty;
        public byte[] Password { get; set; }
        public byte[] PasswordKey { get; set; }

        public string Email { get; set; } = string.Empty;
    
        public List<Post> Posts { get; set; }

        #region 'Country'

        [ForeignKey("Country")]
        public int? CountryId { get; set; } = Constants.ModelConstants.NULL_REFERENCE;

        public Country Country { get; set; }

        #endregion
    }
}
