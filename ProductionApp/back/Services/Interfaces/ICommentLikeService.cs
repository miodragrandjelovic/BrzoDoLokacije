using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services.Interfaces
{
    public interface ICommentLikeService
    {
        public Response IsCommentLiked(int commentID, string username);
        public Response AddLikeOnComment(CommentLike like);
        public Response DeleteLikeFromComment(int likeID);
        public List<CommentLike> GetLikesOfComment(int commentID);
        public List<User> GetUsersWhoLiked(int commentID);
        public int GetCommentLikeCount(int commentID);
    }
}
