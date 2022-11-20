using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services.Interfaces
{
    public interface ICommentDislikeService
    {
        public bool IsCommentDisliked(int commentID);
        public Response ChangeDislikeStateOnComment(Comment comment);
        public Response DeleteDislikeFromComment(int commentID);
        public List<CommentDislike> GetDislikesOfComment(int commentID);
        public List<User> GetUsersWhoDisliked(int commentID);

        public int GetCommentDislikeCount(int commentID);
    }
}
