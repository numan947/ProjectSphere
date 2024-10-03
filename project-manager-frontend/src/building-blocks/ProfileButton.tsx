import { Button, Menu, MenuButton, MenuItem, MenuList } from "@chakra-ui/react";
import { FaSignOutAlt, FaUser } from "react-icons/fa";
import { GrTasks } from "react-icons/gr";
import useAuthStore from "../domain/auth/store/AuthStore";
import { useNavigate } from "react-router-dom";

const ProfileButton = () => {
  const { logout } = useAuthStore();
  const navigate = useNavigate();
  const userName = "John Doe";
  const handleLogOut = () => {
    logout();
    navigate("/");
  };
  return (
    <Menu>
      <MenuButton as={Button} variant="ghost">
        {userName}
      </MenuButton>
      <MenuList>
        <MenuItem icon={<FaUser />}>My Account</MenuItem>
        <MenuItem icon={<GrTasks />}>My Issues</MenuItem>
        <MenuItem icon={<FaSignOutAlt />} onClick={handleLogOut}>
          Logout
        </MenuItem>
      </MenuList>
    </Menu>
  );
};

export default ProfileButton;
