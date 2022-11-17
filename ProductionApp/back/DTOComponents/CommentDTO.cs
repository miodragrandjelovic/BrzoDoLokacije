namespace PyxisKapriBack.DTOComponents
{
    public class CommentDTO
    {
        public int Id { get; set; }
        public string CommentText { get; set; } 

        public string Username { get; set; }
        public string ProfileImage { get; set; }
        public DateTime DateOfCommenting { get; set; }

    }
}
