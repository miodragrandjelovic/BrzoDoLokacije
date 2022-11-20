using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.UI.Interfaces
{
    public interface ICommentUI
    {
        public Response AddComment(NewCommentDTO comment);
        public Response DeleteComment(int commentId);

        public CommentDTO GetComment(int commentId);
        public List<CommentDTO> GetCommentsPost(int postId);
        public Response ChangeLikeStateOnComment(int commentID);
        public Response DeleteLike(int commentID);

        public Response AddDislike(int commentID);
        public Response DeleteDislike(int commentID);
        public Response IsCommentLiked(int commentID);
        public Response IsCommentDisliked(int commentID); 
        public List<UserShortDTO> GetUsersWhoLiked(int commentID);
        public List<UserShortDTO> GetUsersWhoDisliked(int commentID); 

    }
}
