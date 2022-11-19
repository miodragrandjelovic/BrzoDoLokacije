using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services.Interfaces
{
    public interface ICommentDislikeService
    {
        public Response IsCommentDisliked(int commentID);
        public Response AddDislikeOnComment(int commentID);
        public Response DeleteDislikeFromComment(int commentID);
        public List<CommentDislike> GetDislikesOfComment(int commentID);
        public List<User> GetUsersWhoDisliked(int commentID);

        public int GetCommentDislikeCount(int commentID);
    }
}
