namespace PyxisKapriBack.DTOComponents
{
    public class CommentDTO
    {
        public int Id { get; set; }
        public string CommentText { get; set; } 

        public string Username { get; set; }
        public string ProfileImagePath { get; set; }
        public string DateOfCommenting { get; set; }
        public int LikeStatus { get; set; } = 0;
        public int LikeCount { get; set; } = 0;
        public int DislikeCount { get; set; } = 0;
        public int ReplyCount { get; set; } = 0;    


    }
}
