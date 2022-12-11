namespace PyxisKapriBack.DTOComponents
{
    public class PostDTO
    {
        public int Id { get; set; }
        public string Username { get; set; } = string.Empty;
        public string FullProfileImagePath { get; set; } = string.Empty;
        public string FullCoverImagePath { get; set; } = string.Empty;

        //public DateTime DateCreated { get; set; }
        public double NumberOfLikes { get; set; } = 0;
        public int NumberOfViews { get; set; } = 0;
        public bool IsLiked { get; set; } = false;
        public int Grade { get; set; } = 0; 
        public string DateCreated { get; set; } = string.Empty;
        public string Location { get; set; } = string.Empty;
        public string City { get; set; } = string.Empty;
        public string Country { get; set; } = string.Empty;
        public double AverageGrade { get; set; } = 0;
        public int Count { get; set; } = 0; 
        public String? Tags { get; set; }
    }
}
