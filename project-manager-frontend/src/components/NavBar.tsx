import {
  Box,
  Button,
  Divider,
  HStack,
  Image,
  Spacer,
  Text,
  useDisclosure,
} from "@chakra-ui/react";
import icon from "../assets/Icon.svg";
import ColorSwitch from "../building-blocks/ColorSwitch";
import SearchBar from "../building-blocks/SearchBar";
import ProfileButton from "../building-blocks/ProfileButton";
import useAuthStore from "../domain/auth/store/AuthStore";
import LoginOrRegister from "../domain/auth/LoginOrRegister";

const NavBar = () => {
  const { token } = useAuthStore();
  const { isOpen, onClose, onOpen } = useDisclosure();

  return (
    <>
      <HStack minHeight={30} padding={2}>
        <Image src={icon} boxSize="60px" objectFit="cover"></Image>
        <Text
          fontSize={{
            sm: "lg",
            lg: "xl",
            xl: "3xl",
          }}
          as={"b"}
        >
          ProjectSpehere
        </Text>
        <Spacer />
        {token && (
          <>
            <SearchBar />
            <Spacer />
            <ProfileButton />
          </>
        )}
        {!token && (
          <>
            <Button
              variant="outline"
              colorScheme="linkedin"
              transition="background-color 0.2s, transform 0.2s"
              boxShadow="md"
              borderRadius="md"
              onClick={onOpen}
            >
              <Text fontWeight="bold" fontSize="18">
                Login
              </Text>
            </Button>
            <LoginOrRegister isOpen={isOpen} onClose={onClose} />
          </>
        )}
        <ColorSwitch />
      </HStack>
      <Divider />
    </>
  );
};

export default NavBar;
