﻿using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface IFollowDAL
    {
        List<User> GetFollowers(string username); 
        List<User> GetFollowing(string username);
        bool AddFollow(string followerUsername, string followingUsername); 
        bool DeleteFollow(string followerUsername, string followingUsername);
        Follow GetFollow(string followerUsername, string followingUsername, out User follower, out User following);
        Follow GetFollow(string followerUsername, string followingUsername); 
        bool IsFollowed(string followerUsername, string followingUsername);

        List<User> SearchFollowers(string username, string search); 
        List<User> SearchFollowing(string username, string search);
    }
}
