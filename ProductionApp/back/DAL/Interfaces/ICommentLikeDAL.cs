using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ICommentLikeDAL
    {
        public bool IsCommentLiked(int commentID, string username); 
        public bool AddLikeOnComment(CommentLike like); 
        public bool DeleteLikeFromComment(int likeID);
        public List<CommentLike> GetLikesOfComment(int commentID);
        public List<User> GetUsersWhoLiked(int commentID);
        public CommentLike GetCommentLike(int likeID); 
    }
}
