using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ICommentLikeDAL
    {
        public bool IsCommentLiked(int commentID, string username); 
        public bool AddLikeOnComment(CommentLike like); 
        public bool DeleteLikeFromComment(CommentLike like);
        public List<CommentLike> GetLikesOfComment(int commentID);
    }
}
