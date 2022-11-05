namespace PyxisKapriBack.Models
{
    public class PostDTO
    {
        public string CoverImage { get; set; }
        //Dodati i listu svih slika
        public int NumberOfLikes { get; set; } = 0;
        public int NumberOfViews { get; set; } = 0;
    }
}
