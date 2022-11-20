using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ICommentDislikeDAL
    {
        public bool IsCommentDisliked(string username, int commentID);
        public bool AddDislikeOnComment(CommentDislike dislike);
        public bool DeleteDislikeFromComment(string username, int commentID);
        public bool CheckIfUserDislike(int userID, int commentID);
        public List<CommentDislike> GetDislikesOfComment(int commentID);
        public List<User> GetUsersWhoDisliked(int commentID);
        public CommentDislike GetCommentDislike(int dislikeID);
        public CommentDislike GetCommentDislike(string username, int commentID);
    }
}
