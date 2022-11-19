using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services.Interfaces
{
    public interface ICommentLikeService
    {
        public Response IsCommentLiked(int commentID);
        public Response AddLikeOnComment(int commentID);
        public Response DeleteLikeFromComment(int commentID);
        public List<CommentLike> GetLikesOfComment(int commentID);
        public List<User> GetUsersWhoLiked(int commentID);
        public int GetCommentLikeCount(int commentID);
    }
}
