using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ICommentDislikeDAL
    {
        public bool IsCommentDisliked(int commentID, string username);
        public bool AddDislikeOnComment(CommentDislike dislike);
        public bool DeleteDislikeFromComment(int dislikeID);
        public List<CommentDislike> GetDislikesOfComment(int commentID);
        public List<User> GetUsersWhoDisliked(int commentID);
        public CommentDislike GetCommentDislike(int dislikeID); 
    }
}
