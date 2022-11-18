namespace PyxisKapriBack.Models
{
    public class Comment
    {
        [Key]
        public int Id { get; set; }

        [MaxLength(Constants.ModelConstants.LENGTH_DESCRIPTION)]
        public string Text { get; set; }
        public List<Comment>? Replies { get; set; } = null;
        public DateTime DateCreated { get; set; }

        #region 'Post'
        public int PostId { get; set; }
        public Post Post { get; set; }
        #endregion

        #region 'User'
        public int UserId { get; set; }
        public User User { get; set; }
        #endregion

        #region 'Comment'
        public int CommentParentId { get; set; } = 0; 
        public Comment? CommentParent { get; set; }
        #endregion
    }
}
