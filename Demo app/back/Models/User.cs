namespace PyxisKapriBack.Models
{
    public class User
    {
        [Key]
        public int Id { get; set; }

        [MaxLength(50)]
        public string Username { get; set; } = string.Empty;
        public string Password { get; set; }

        public string Email { get; set; } = string.Empty;

    }
}
