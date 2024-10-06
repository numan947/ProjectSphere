import { Box } from "@chakra-ui/react";
import { Outlet } from "react-router-dom";
import NavBar from "./NavBar";

const Layout = () => {
  return (
    <>
      <Box>
        <NavBar />
      </Box>
      <Box padding={5}>
        <Outlet />
      </Box>
    </>
  );
};

export default Layout;
