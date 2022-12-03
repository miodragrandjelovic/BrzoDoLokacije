namespace PyxisKapriBack.DTOComponents
{
    public class PostDTO
    {
        public int Id { get; set; }
        public string Username { get; set; } = string.Empty;
        public string FullProfileImagePath { get; set; } = string.Empty;
        public string FullCoverImagePath { get; set; } = string.Empty;

        //public DateTime DateCreated { get; set; }
        public int NumberOfLikes { get; set; } = 0;
        public int NumberOfViews { get; set; } = 0;
        public bool IsLiked { get; set; } = false;
        public string DateCreated { get; set; } = string.Empty;
    }
}
