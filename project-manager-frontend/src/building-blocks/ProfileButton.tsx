import {
  Button,
  Menu,
  MenuButton,
  MenuItem,
  MenuList,
  Text,
} from "@chakra-ui/react";
import { FaSignOutAlt, FaUser } from "react-icons/fa";
import { GrTasks } from "react-icons/gr";
import useAuthStore from "../domain/auth/AuthStore";
import { useNavigate } from "react-router-dom";

const ProfileButton = () => {
  const { logout, userFullName } = useAuthStore();
  const navigate = useNavigate();
  const handleLogOut = () => {
    logout();
    navigate("/");
  };
  return (
    <Menu>
      <MenuButton
        as={Button}
        variant="link"
        justifyItems="center"
        justifyContent="center"
        colorScheme="blue"
      >
        <Text>{userFullName}</Text>
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
