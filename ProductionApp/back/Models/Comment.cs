﻿namespace PyxisKapriBack.Models
{
    public class Comment
    {
        [Key]
        public int Id { get; set; }

        [MaxLength(Constants.ModelConstants.LENGTH_COMMENT)]
        public string Text { get; set; }
        public List<Comment>? Replies { get; set; } = null;
        public List<CommentLike>? Likes { get; set; } = null;
        public List<CommentDislike>? Dislikes { get; set; } = null;
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
        public int? CommentParentId { get; set; } = null;
        public Comment? CommentParent { get; set; } = null;
        #endregion
    }
}
