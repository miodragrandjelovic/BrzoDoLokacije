using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ICommentLikeDAL
    {
        public bool IsCommentLiked(string username, int commentID); 
        public bool AddLikeOnComment(CommentLike like); 
        public bool DeleteLikeFromComment(string username, int commentID);
        public List<CommentLike> GetLikesOfComment(int commentID);
        public List<User> GetUsersWhoLiked(int commentID);
        public CommentLike GetCommentLike(int likeID);
        public CommentLike GetCommentLike(string username, int commentID); 
    }
}
