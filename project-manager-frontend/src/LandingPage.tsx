import { Center, Text } from "@chakra-ui/react";
import useAuthStore from "./domain/auth/AuthStore";
import { Navigate } from "react-router-dom";

function LandingPage() {
  const { token } = useAuthStore();
  if (token) {
    return <Navigate to="/home" />;
  }

  return (
    <Center>
      <Text>Hello World</Text>
    </Center>
  );
}

export default LandingPage;
