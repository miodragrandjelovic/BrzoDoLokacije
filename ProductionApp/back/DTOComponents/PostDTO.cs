namespace PyxisKapriBack.Models
{
    public class PostDTO
    {
        public int Id { get; set; }
        public string Username { get; set; } = string.Empty;
        public string ProfileImage { get; set; } = string.Empty;
        public string CoverImage { get; set; } = string.Empty;
        public int NumberOfLikes { get; set; } = 0;
        public int NumberOfViews { get; set; } = 0;
    }
}
