using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ICommentDAL
    {
        public int AddComment(Comment Comment); 
        public bool DeleteComment(int CommitID);
        public bool UpdateComment(Comment Comment); 

        public Comment GetComment(int CommentID); 
        public List<Comment> GetCommentsPost(int PostID);

        public List<Comment> GetReplies(int CommentID); 

    }
}
