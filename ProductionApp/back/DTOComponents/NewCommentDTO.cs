namespace PyxisKapriBack.DTOComponents
{
    public class NewCommentDTO
    {
        public int PostId { get; set; }
        public int CommentParentId { get; set; } = 0;
        public string Comment { get; set; } = string.Empty;
    }
}
