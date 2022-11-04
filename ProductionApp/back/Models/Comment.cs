﻿namespace PyxisKapriBack.Models
{
    public class Comment
    {
        [Key]
        public int Id { get; set; }

        public List<Comment>? Replies { get; set; } = null; 

        #region 'Post'
        public int PostId { get; set; }
        public Post Post { get; set; }
        #endregion

        #region 'User'
        public int UserId { get; set; }
        public User User { get; set; }
        #endregion

        #region 'Comment'
        public int CommentParentId { get; set; }
        public Comment CommentParent { get; set; }
        #endregion
    }
}