using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ICommentDAL
    {
        public bool AddComment(Comment Comment); 
        public bool DeleteComment(Comment Comment);

        public Comment GetComment(int CommentID); 
        public List<Comment> GetCommentsPost();

    }
}
